import QtQuick 2.0
import Sailfish.Silica 1.0
import CustomCppClasses.Module 1.0

Page {
    allowedOrientations: Orientation.All

    KotlinNativeVM { id: viewModel }

    PageHeader {
        id: header
        title: qsTr("Kotlin Native")
    }

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
                top: parent.top; bottom: btAction1.top;
                margins: Theme.horizontalPageMargin
            }
            color: palette.highlightColor
            font.pixelSize: Theme.fontSizeSmall
            wrapMode: Text.WordWrap
            text: viewModel.text
            clip: true
        }

        Button {
            id: btAction1
            anchors { left: parent.left; right: parent.right;
                bottom: btAction2.top;
                margins: Theme.horizontalPageMargin
            }
            text: qsTr("Std")
            onClicked: viewModel.std()

        }

        Button {
            id: btAction2
            anchors { left: parent.left; right: parent.right;
                bottom: btAction3.top;
                margins: Theme.horizontalPageMargin
            }
            text: qsTr("Serialization")
            onClicked: viewModel.serialization()
        }

        Button {
            id: btAction3
            anchors { left: parent.left; right: parent.right;
                bottom: btAction4.top;
                margins: Theme.horizontalPageMargin
            }
            text: qsTr("Coroutines")
            onClicked: viewModel.coroutines()
        }

        Button {
            id: btAction4
            anchors { left: parent.left; right: parent.right;
                bottom: btAction5.top;
                margins: Theme.horizontalPageMargin
            }
            text: qsTr("Ktor")
            onClicked: viewModel.ktor()
        }

        Button {
            id: btAction5
            anchors { left: parent.left; right: parent.right;
                bottom: btAction6.top;
                margins: Theme.horizontalPageMargin
            }
            text: qsTr("DB")
            onClicked: viewModel.db()
        }

        Button {
            id: btAction6
            anchors { left: parent.left; right: parent.right;
                bottom: btAction7.top;
                margins: Theme.horizontalPageMargin
            }
            text: qsTr("TEST 1")
            onClicked: viewModel.test1()
        }

        Button {
            id: btAction7
            anchors { left: parent.left; right: parent.right;
                bottom: parent.bottom;
                margins: Theme.horizontalPageMargin
            }
            text: qsTr("TEST2")
            onClicked: viewModel.test2()
        }
    }
}
