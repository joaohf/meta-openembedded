# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING-LGPLv2.1;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://github.com/FRRouting/frr;protocol=https;branch=master"

# Modify these as desired
PV = "8.1-dev+git${SRCPV}"
SRCREV = "32694c41bb298075c19b2cd50525bee3a336ccec"

S = "${WORKDIR}/git"

DEPENDS = "elfutils bison flex"

inherit perlnative pkgconfig autotools python3native

EXTRA_OECONF  = " \
	       --disable-babeld \
	       --disable-bgp-announce \
	       --disable-bgp-vnc \
	       --disable-bgpd \
	       --disable-doc \
	       --disable-eigrpd \
	       --disable-isisd \
	       --disable-ldpd \
	       --disable-nhrpd \
 	       --disable-ospf6d \
 	       --disable-ospfapi \
 	       --disable-ospfclient \
 	       --disable-ospfd \
 	       --disable-pbrd \
 	       --disable-pimd \
 	       --disable-ripd \
 	       --disable-ripngd \
 	       --disable-silent-rules \
	       --disable-vtysh \
 	       --disable-watchfrr \
	       --disable-zebra \
	       --disable-zeromq \
	       --enable-clippy-only \
	       "

do_install() {
    install -d ${D}${bindir}
    install -c -m 755 ${B}/lib/clippy ${D}${bindir}/clippy
}

BBCLASSEXTEND = "native nativesdk"
