# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

SUMMARY = "YANG data modeling language library"
HOMEPAGE = "https://github.com/CESNET/libyang"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
#   distro/pkg/deb/copyright
#   tools/lint/linenoise/LICENSE
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f3916d7d8d42a6508d0ea418cfff10ad \
                    file://distro/pkg/deb/copyright;md5=1ec36238fb21a6efbe7604e2071064b5 \
                    file://tools/lint/linenoise/LICENSE;md5=faa55ac8cbebebcb4a84fe1ea2879578"

SRC_URI = "git://github.com/CESNET/libyang;protocol=https;branch=master \
           file://0001-Use-pkg-config-and-find-libpcre2-8-version.patch \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "69d9fff65abb58beb0bb6aa9ecacd572ca1dfc56"

S = "${WORKDIR}/git"

DEPENDS = "libpcre2"

RPROVIDES_${PN} = "libyang"

# NOTE: unable to map the following CMake package dependencies: CMocka PCRE2
# NOTE: spec file indicates the license may be "BSD-3-Clause"
inherit cmake pkgconfig

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX:PATH=/usr -DCMAKE_BUILD_TYPE:String=Release "
