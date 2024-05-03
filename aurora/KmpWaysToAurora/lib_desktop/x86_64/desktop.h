#ifndef ___HOME_MERSDK_KMPWAYSTOAURORA_DESKTOP_BUILD_NATIVE_NATIVECOMPILE_DESKTOP_H
#define ___HOME_MERSDK_KMPWAYSTOAURORA_DESKTOP_BUILD_NATIVE_NATIVECOMPILE_DESKTOP_H

#include <graal_isolate.h>


#if defined(__cplusplus)
extern "C" {
#endif

int run_main(int argc, char** argv);

char* platform(graal_isolatethread_t*);

#if defined(__cplusplus)
}
#endif
#endif
