#include <auroraapp/auroraapp.h>
#include <QtQuick>

#include "kotlinnativevm.h"
#include "kotlinjtnvm.h"
#include <libshared_api.h>

int main(int argc, char *argv[])
{
    qmlRegisterType<KotlinNativeVM>("CustomCppClasses.Module", 1, 0, "KotlinNativeVM");
    qmlRegisterType<KotlinJtnVM>("CustomCppClasses.Module", 1, 0, "KotlinJtnVM");

    QScopedPointer<QGuiApplication> application(Aurora::Application::application(argc, argv));
    application->setOrganizationName(QStringLiteral("com.den3000.kmpwaystoaurora"));
    application->setApplicationName(QStringLiteral("KmpWaysToAurora"));

    QScopedPointer<QQuickView> view(Aurora::Application::createView());
    view->setSource(Aurora::Application::pathTo(QStringLiteral("qml/KmpWaysToAurora.qml")));
    view->show();

    return application->exec();
}
