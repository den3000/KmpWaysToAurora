TARGET = com.den3000.kmpwaystoaurora.KmpWaysToAurora

contains(QMAKE_HOST.arch, x86_64):{
    SHARED_LIB_PATH=libs/x86_64 # path to libshared.a
}

contains(QMAKE_HOST.arch, aarch64):{
    SHARED_LIB_PATH=libs/aarch64 # path to libshared.a
}

contains(QMAKE_HOST.arch, armv7l):{
    error("Unsupported architecture")
}

CONFIG += \
    auroraapp

PKGCONFIG += \

SOURCES += \
    src/main.cpp \

HEADERS += \
    $${SHARED_LIB_PATH}/libshared_api.h \

DISTFILES += \
    qml/pages/KotlinJvmToNativePage.qml \
    qml/pages/KotlinJSPage.qml \
    qml/pages/KotlinNativePage.qml \
    rpm/com.den3000.kmpwaystoaurora.KmpWaysToAurora.spec \

AURORAAPP_ICONS = 86x86 108x108 128x128 172x172

CONFIG += auroraapp_i18n

TRANSLATIONS += \
    translations/com.den3000.kmpwaystoaurora.KmpWaysToAurora.ts \
    translations/com.den3000.kmpwaystoaurora.KmpWaysToAurora-ru.ts \

unix:!macx: LIBS += -L$$PWD/$${SHARED_LIB_PATH}/ -lshared

INCLUDEPATH += $$PWD/$${SHARED_LIB_PATH}
DEPENDPATH += $$PWD/$${SHARED_LIB_PATH}

unix:!macx: PRE_TARGETDEPS += $$PWD/$${SHARED_LIB_PATH}/libshared.a
