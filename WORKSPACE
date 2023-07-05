load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_jar")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "2.5"

RULES_JVM_EXTERNAL_SHA = "249e8129914be6d987ca57754516be35a14ea866c616041ff0cd32ea94d2f3a1"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

# maven_install.json is the source of truth. Versions available at runtime come from what is specified in maven_install.
# if an update is made in the maven_install below, it will not be reflected in the maven_install.json automaitcally.
# To repin everything in maven_install.json to what is specified here, we need to run bazel run @unpinned_maven//:pin.
maven_install(
    artifacts = [
        "junit:junit:4.12",
        "androidx.test.ext:truth:1.5.0",
    ],
    fetch_sources = True,
    maven_install_json = "//:maven_install.json",
    repositories = [
        "http://maven.org/maven2",
        "https://maven.google.com",
    ],
)

#TODO(@grantbaum): uncomment and get pinned maven_install working.
load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()
