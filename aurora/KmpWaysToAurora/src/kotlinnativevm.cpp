#include "kotlinnativevm.h"

const char ** KtTriggerLambda::captureArg1;

void KtTriggerLambda::capture(const char **captureArg1) {
    KtTriggerLambda::captureArg1 = captureArg1;
}

void KtTriggerLambda::callback() {
    *(KtTriggerLambda::captureArg1) = "Triggered from lambda on Aurora";
}
