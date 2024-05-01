import QtQuick 2.0
import Sailfish.Silica 1.0
import "../kmp" as KMP

Page {
    allowedOrientations: Orientation.All

    PageHeader {
        id: header
        title: qsTr("Kotlin JS")
    }

    property string strText: "No value"

    Column {
        id: layout
        width: parent.width
        spacing: 16
        anchors {
            top: header.bottom
            left: parent.left
            right: parent.right
            bottom: parent.bottom
        }

        Label {
            id: text
            anchors { left: parent.left; right: parent.right;
                top: parent.top; bottom: row4.top;
                margins: Theme.horizontalPageMargin
            }
            color: palette.highlightColor
            font.pixelSize: Theme.fontSizeSmall
            wrapMode: Text.WordWrap
            text: strText
            clip: true
        }

        Row {
            id: row4
            spacing: Theme.horizontalPageMargin
            anchors { left: parent.left; right: parent.right;
                bottom: row3.top;
                margins: Theme.horizontalPageMargin
            }

            Button {
                text: qsTr("Std")
                onClicked: platformJS()
            }

            Button {
                text: qsTr("Serialization")
                onClicked: {}
            }
        }

        Row {
            id: row3
            spacing: Theme.horizontalPageMargin
            anchors { left: parent.left; right: parent.right;
                bottom: row2.top;
                margins: Theme.horizontalPageMargin
            }

            Button {
                text: qsTr("Coroutines")
                onClicked: {}
            }

            Button {
                text: qsTr("Flow")
                onClicked: {}
            }
        }

        Row {
            id: row2
            spacing: Theme.horizontalPageMargin
            anchors { left: parent.left; right: parent.right;
                bottom: row1.top;
                margins: Theme.horizontalPageMargin
            }


            Button {
                text: qsTr("Ktor")
                onClicked: getKtorIoWelcomePageAsTextJS()
            }

            Button {
                text: qsTr("DB")
                onClicked: {}
            }
        }

        Row {
            id: row1
            spacing: Theme.horizontalPageMargin
            anchors { left: parent.left; right: parent.right;
                bottom: parent.bottom;
                margins: Theme.horizontalPageMargin
            }

            Button {
                text: qsTr("TEST 1")
                onClicked: {}
            }

            Button {
                text: qsTr("TEST 2")
                onClicked: {}
            }
        }
    }

    KMP.Shared {
        id: libKMPShared
        onCompleted: {
            strText = "KMP LOADED"
        }
    }

    function platformJS() {
        libKMPShared.run(
            "shared.platformJS()",
            function(result) {
                strText = result
            },
            function(error) {
                console.log(error)
            }
        );
    }

    function getKtorIoWelcomePageAsTextJS() {
        libKMPShared.runAsync(
            "shared.getKtorIoWelcomePageAsTextJS()",
            function(result) {
                strText = result
            },
            function(error) {
                console.log(error)
            }
        );
    }
}
