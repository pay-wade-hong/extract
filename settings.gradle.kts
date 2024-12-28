rootProject.name = "extract"
include(
    "extract-domain",
    "extract-function",
    "extract-function:extract-account-function",
    "extract-function:extract-payment-function",
    "extract-function:extract-point-function",
    "extract-function:extract-hadoop-function",
    "extract-function:redis-save-function",
    "infra:redis",
    "infra:test-redis",
)