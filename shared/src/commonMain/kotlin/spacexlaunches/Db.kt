package foo.spacexlaunches

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.den3000.kmpwaystoaurora.Database
import com.den3000.kmpwaystoaurora.LaunchQueries
import foo.DriverFactory
import spacexlaunches.Links
import spacexlaunches.Patch
import spacexlaunches.RocketLaunch

internal class Db(private val databaseDriverFactory: DriverFactory) {
    private var database: Database? = null
    private var dbQuery: LaunchQueries? = null

    suspend fun start() {
        database = databaseDriverFactory.createDriver()?.let { Database(it) }
        dbQuery = database?.launchQueries
    }

    internal suspend fun getAllLaunches(): List<RocketLaunch> {
        return dbQuery?.selectAllLaunchesInfo(::mapLaunchSelecting)?.awaitAsList() ?: emptyList()
    }

    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        patchUrlSmall: String?,
        patchUrlLarge: String?,
        articleUrl: String?
    ): RocketLaunch {
        return RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            details = details,
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess,
            links = Links(
                patch = Patch(
                    small = patchUrlSmall,
                    large = patchUrlLarge
                ),
                article = articleUrl
            )
        )
    }

    internal suspend fun clearAndCreateLaunches(launches: List<RocketLaunch>) {
        dbQuery?.transaction {
            dbQuery?.removeAllLaunches()
            launches.forEach { launch ->
                dbQuery?.insertLaunch(
                    flightNumber = launch.flightNumber.toLong(),
                    missionName = launch.missionName,
                    details = launch.details,
                    launchSuccess = launch.launchSuccess ?: false,
                    launchDateUTC = launch.launchDateUTC,
                    patchUrlSmall = launch.links.patch?.small,
                    patchUrlLarge = launch.links.patch?.large,
                    articleUrl = launch.links.article
                )
            }
        }
    }
}