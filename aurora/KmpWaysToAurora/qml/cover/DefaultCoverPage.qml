import QtQuick 2.0
import Sailfish.Silica 1.0

CoverBackground {
    objectName: "defaultCover"

    CoverPlaceholder {
        objectName: "placeholder"
        text: qsTr("Template")
        icon {
            source: Qt.resolvedUrl("../icons/KmpWaysToAurora.svg")
            sourceSize { width: icon.width; height: icon.height }
        }
        forceFit: true
    }
}
