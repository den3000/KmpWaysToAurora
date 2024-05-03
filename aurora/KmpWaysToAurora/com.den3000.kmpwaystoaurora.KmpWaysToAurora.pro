TARGET = com.den3000.kmpwaystoaurora.KmpWaysToAurora

contains(QMAKE_HOST.arch, armv7l):{ error("Unsupported architecture") }
contains(QMAKE_HOST.arch, x86_64):{ SHARED_LIB_ARCH_TYPE_PATH=lib_shared/x86_64 }
contains(QMAKE_HOST.arch, aarch64):{ SHARED_LIB_ARCH_TYPE_PATH=lib_shared/aarch64 }
CONFIG(debug, debug|release):{ SHARED_LIB_BUILD_TYPE_PATH=debug }
CONFIG(release, debug|release):{ SHARED_LIB_BUILD_TYPE_PATH=release }

# path to libshared.a
SHARED_LIB_PATH=$${SHARED_LIB_ARCH_TYPE_PATH}/$${SHARED_LIB_BUILD_TYPE_PATH}

# improve this
DESKTOP_LIB_PATH=lib_desktop/x86_64

CONFIG += \
    auroraapp

PKGCONFIG += \


desktop_library_install.path = /usr/share/com.den3000.kmpwaystoaurora.KmpWaysToAurora/lib/
desktop_library_install.files = $$PWD/$${DESKTOP_LIB_PATH}/*.so*
desktop_library_install.CONFIG = no_check_exist
INSTALLS += desktop_library_install

SOURCES += \
    src/main.cpp \

HEADERS += \
    $${SHARED_LIB_PATH}/libshared_api.h \
    src/kotlinnativevm.h

DISTFILES += \
    qml/pages/AboutPage.qml \
    qml/pages/KotlinJsPage.qml \
    qml/pages/KotlinJsViewModel.qml \
    qml/pages/KotlinJvmToNativePage.qml \
    qml/pages/KotlinNativePage.qml \
    qml/pages/MainPage.qml \
    qml/KmpWaysToAurora.qml \
    rpm/com.den3000.kmpwaystoaurora.KmpWaysToAurora.spec \

AURORAAPP_ICONS = 86x86 108x108 128x128 172x172

CONFIG += auroraapp_i18n

TRANSLATIONS += \
    translations/com.den3000.kmpwaystoaurora.KmpWaysToAurora.ts \
    translations/com.den3000.kmpwaystoaurora.KmpWaysToAurora-ru.ts \

unix:!macx: LIBS += -L$$PWD/$${SHARED_LIB_PATH} -lshared -lcurl -lsqlite3 \
    -L$$PWD/$${DESKTOP_LIB_PATH}/ -ldesktop

INCLUDEPATH += $$PWD/$${SHARED_LIB_PATH} \
    $$PWD/$${DESKTOP_LIB_PATH}/
DEPENDPATH += $$PWD/$${SHARED_LIB_PATH} \
    $$PWD/$${DESKTOP_LIB_PATH}/

unix:!macx: PRE_TARGETDEPS += $$PWD/$${SHARED_LIB_PATH}/libshared.a
