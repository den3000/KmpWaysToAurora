import QtQuick 2.0
import Sailfish.Silica 1.0

Page {
    allowedOrientations: Orientation.All

    Column {
        id: layout
        width: parent.width
        spacing: 16
        anchors.centerIn: parent

        Button {
            anchors { left: parent.left; right: parent.right; margins: Theme.horizontalPageMargin }
            text: qsTr("Kotlin Native")
            onClicked: pageStack.push(Qt.resolvedUrl("KotlinNativePage.qml"))

        }

        Button {
            anchors { left: parent.left; right: parent.right; margins: Theme.horizontalPageMargin }
            text: qsTr("Kotlin JS")
            onClicked: pageStack.push(Qt.resolvedUrl("KotlinJSPage.qml"))
        }

        Button {
            anchors { left: parent.left; right: parent.right; margins: Theme.horizontalPageMargin }
            text: qsTr("Kotlin JVM to Native")
            onClicked: pageStack.push(Qt.resolvedUrl("KotlinJvmToNativePage.qml"))
        }
    }
}
