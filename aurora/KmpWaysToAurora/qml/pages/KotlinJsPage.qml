import QtQuick 2.0
import Sailfish.Silica 1.0

Page {
    allowedOrientations: Orientation.All

    KotlinJsViewModel {
        id: vm
    }

    PageHeader {
        id: header
        title: qsTr("Kotlin JS")
    }

    property string strText: ""

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
                onClicked: vm.std(function(result){
                    strText = result
                })
            }

            Button {
                text: qsTr("Serialization")
                onClicked: vm.serialization(function(result){
                    strText = result
                })
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
                onClicked: {
                    strText = "Coroutine started"
                    vm.coroutine(function(result){
                        strText = result
                    })
                }
            }

            Button {
                text: qsTr("Flow")
                onClicked: {
                    strText = "Flow started"
                    vm.flow(function(result){
                        strText = result
                    })
                }
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
                onClicked: {
                    strText = "Request started"
                    vm.ktor(function(result){
                        strText = result
                    })
                }
            }

            Button {
                text: qsTr("DB")
                onClicked: strText = "SqlDelight not available for Aurora JS target"
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
                onClicked: strText = "SqlDelight not available for Aurora JS target"
            }

            Button {
                text: qsTr("TEST 2")
                onClicked: strText = "SqlDelight not available for Aurora JS target"
            }
        }
    }
}
