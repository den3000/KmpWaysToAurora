#ifndef ___HOME_MERSDK_KMPWAYSTOAURORA_DESKTOP_BUILD_NATIVE_NATIVECOMPILE_DESKTOP_H
#define ___HOME_MERSDK_KMPWAYSTOAURORA_DESKTOP_BUILD_NATIVE_NATIVECOMPILE_DESKTOP_H

#include <graal_isolate.h>


#if defined(__cplusplus)
extern "C" {
#endif

int run_main(int argc, char** argv);

char* jtn_platform(graal_isolatethread_t*);

void jtn_test2(graal_isolatethread_t*, void *, size_t);

#if defined(__cplusplus)
}
#endif
#endif
