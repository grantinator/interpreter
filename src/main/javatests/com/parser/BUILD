java_library(
    name = "tests",
    srcs = glob(["*.java"]),
    deps = [
        "//src/main/java/com/ast",
        "//src/main/java/com/lexer",
        "//src/main/java/com/parser",
        "//src/main/java/com/token",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
    ],
)

java_test(
    name = "AllTests",
    size = "small",
    test_class = "main.javatests.com.parser.ParserTests",
    runtime_deps = [":tests"],
)
