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
                top: parent.top; bottom: row4.top;
                margins: Theme.horizontalPageMargin
            }
            color: palette.highlightColor
            font.pixelSize: Theme.fontSizeSmall
            wrapMode: Text.WordWrap
            text: viewModel.text
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
                onClicked: viewModel.std()

            }

            Button {
                text: qsTr("Serialization")
                onClicked: viewModel.serialization()
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
                onClicked: viewModel.coroutines()
            }

            Button {
                text: qsTr("Flow")
                onClicked: viewModel.flow()
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
                onClicked: viewModel.ktor()
            }

            Button {
                text: qsTr("DB")
                onClicked: viewModel.db()
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
                onClicked: viewModel.test1()
            }

            Button {
                text: qsTr("TEST 2")
                onClicked: viewModel.test2()
            }
        }
    }
}
