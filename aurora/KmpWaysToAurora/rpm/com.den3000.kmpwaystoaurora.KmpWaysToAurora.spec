%define __provides_exclude_from ^%{_datadir}/.*$
%define __requires_exclude ^libdesktop.*$

Name:       com.den3000.kmpwaystoaurora.KmpWaysToAurora
Summary:    My Aurora OS Application
Version:    0.1
Release:    1
License:    BSD-3-Clause
URL:        https://auroraos.ru
Source0:    %{name}-%{version}.tar.bz2

Requires:   sailfishsilica-qt5 >= 0.10.9
Requires:   sailfish-components-webview-qt5
Requires:   sailfish-components-webview-qt5-pickers
Requires:   sailfish-components-webview-qt5-popups
BuildRequires:  pkgconfig(auroraapp)
BuildRequires:  pkgconfig(Qt5Core)
BuildRequires:  pkgconfig(Qt5Qml)
BuildRequires:  pkgconfig(Qt5Quick)
BuildRequires:  pkgconfig(sqlite3)
BuildRequires:  pkgconfig(libcurl)


%description
Short description of my Aurora OS Application

%prep
%autosetup

%build
%qmake5
%make_build

%install
%make_install

%files
%{_bindir}/%{name}
%{_datadir}/%{name}
%{_datadir}/applications/%{name}.desktop
%{_datadir}/icons/hicolor/*/apps/%{name}.png
