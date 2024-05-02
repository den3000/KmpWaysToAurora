import QtQuick 2.0
import "../kmp" as KMP

Item {
    KMP.Shared {
        id: libKMPShared
        onCompleted: { console.log("KMP LOADED") }
    }

    function std(callback) {
        libKMPShared.run(
            "shared.stdJS()",
            callback,
            function(error) { console.log(error) }
        );
    }

    function serialization(callback) {
        libKMPShared.run(
            "shared.serializationJS()",
            callback,
            function(error) { console.log(error) }
        );
    }

    function coroutine(callback) {
        libKMPShared.runAsync(
            "shared.coroutineJS()",
            callback,
            function(error) { console.log(error) }
        );
    }

    function flow(callback) {
        libKMPShared.runAsync(
            "shared.flowJS()",
            callback,
            function(error) { console.log(error) }
        );
    }

    function ktor(callback) {
        libKMPShared.runAsync(
            "shared.ktorJS()",
            callback,
            function(error) { console.log(error) }
        );
    }

    function db(callback) {
        libKMPShared.runAsync(
            "shared.dbJS()",
            callback,
            function(error) { console.log(error) }
        );
    }
}
