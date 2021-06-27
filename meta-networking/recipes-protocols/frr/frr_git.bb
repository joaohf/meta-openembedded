SUMMARY = "The FRRouting Protocol Suite"
DESCRIPTION = "FRR is free software that implements and manages various IPv4 and IPv6 \
routing protocols. It runs on nearly all distributions of Linux and BSD and supports all \
modern CPU architectures."
HOMEPAGE = "https://github.com/FRRouting/frr"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/FRRouting/frr;protocol=https;branch=stable/8.0 \
           file://frr.service \
           file://daemons \
           file://frr.conf \
           file://vtysh.conf"

RDEPENDS_frr = "bash python3-core"

PV = "8.0+git${SRCREV}"
SRCREV = "5de7dbc1d56cda6ed392e75e145c170178014dd2"

S = "${WORKDIR}/git"

DEPENDS = "bison-native systemd libxcrypt flex-native libpcre c-ares json-c grpc \
           protobuf-c protobuf sqlite3 openssl readline libunwind libcap yang clippy-native"

PACKAGECONFIG ??= " \
    ${@bb.utils.filter('DISTRO_FEATURES', 'pam', d)} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)} \
    ospfd \
    "

PACKAGECONFIG[cap] = "--enable-capabilities,--disable-capabilities,libcap"
PACKAGECONFIG[pam] = "--with-libpam, --without-libpam, libpam"
PACKAGECONFIG[systemd] = "--enable-systemd,--disable-systemd,systemd"
PACKAGECONFIG[ospfd] = "--enable-ospfd,,"

inherit perlnative pkgconfig autotools useradd systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "frr.service"

EXTRA_OECONF = "\
    --disable-doc \
    --with-clippy=${STAGING_DIR_NATIVE}/usr/bin/clippy \
    --sysconfdir=${sysconfdir}/frr \
    --sbindir=${libdir}/frr \
    --localstatedir=${localstatedir}/run/frr \
    --prefix=${prefix} \
    --enable-user=frr \
    --enable-group=frr \
    --enable-vty-group=frrvty \
    "

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/frr.service ${D}${systemd_unitdir}/system
    install -d ${D}${sysconfdir}/frr
    install -m 0644 ${WORKDIR}/daemons ${D}${sysconfdir}/frr/daemons
    install -m 0644 ${WORKDIR}/frr.conf ${D}${sysconfdir}/frr/frr.conf
    install -m 0644 ${WORKDIR}/vtysh.conf ${D}${sysconfdir}/frr/vtysh.conf
    sed -i -e "s|/usr/bin/python|/usr/bin/env python3|g" ${D}${libdir}/frr/frr-reload.py
}

# Add frr's user and group
USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system frr ; --system frrvty"
USERADD_PARAM_${PN} = "--system --home ${localstatedir}/run/frr/ -M -g frr -G frrvty --shell /bin/false frr"

FILES_${PN} += "${datadir}/yang"