import QtQuick 2.0
import Sailfish.Silica 1.0

Page {
    allowedOrientations: Orientation.All

    PageHeader { title: qsTr("Kotlin Native") }

    Column {
        id: layout
        width: parent.width
        spacing: 16
        anchors.centerIn: parent

        Label {
            anchors { left: parent.left; right: parent.right; margins: Theme.horizontalPageMargin }
            color: palette.highlightColor
            font.pixelSize: Theme.fontSizeSmall
            textFormat: Text.RichText
            wrapMode: Text.WordWrap
            text: qsTr("Text")
        }

        Button {
            anchors { left: parent.left; right: parent.right; margins: Theme.horizontalPageMargin }
            text: qsTr("Action")
            onClicked: {

            }
        }

    }
}
