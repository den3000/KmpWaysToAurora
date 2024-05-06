#ifndef KOTLINJTNVM_H
#define KOTLINJTNVM_H

#include <QObject>
#include <QDebug>
#include <QQuickItem>

#include <desktop.h>
#include <functional>

class KotlinJtnVM : public QObject
{
    struct TestTwoRes {
        KotlinJtnVM * that;
        graal_isolate_t *isolate;
        graal_isolatethread_t *thread;
    } testTwoRes;

    Q_OBJECT
    Q_PROPERTY(QString text MEMBER m_text NOTIFY textChanged)

public:
    explicit KotlinJtnVM(QObject *parent = nullptr): QObject(parent) { qDebug(); };
    ~KotlinJtnVM() { qDebug(); }

public slots:
    void platform() {
        graal_isolate_t *isolate = NULL;
        graal_isolatethread_t *thread = NULL;

        if (graal_create_isolate(NULL, &isolate, &thread) != 0) {
            qDebug() << "initialization error\n";
        }

        updateText((char *)jtn_platform(thread));

        graal_tear_down_isolate(thread);
    }

    void test2() {
        graal_isolate_t *isolate = NULL;
        graal_isolatethread_t *thread = NULL;

        if (graal_create_isolate(NULL, &isolate, &thread) != 0) {
            qDebug() << "initialization error\n";
        }

        auto noCapture = [](char * text, size_t p) {
            auto vm = reinterpret_cast<KotlinJtnVM *>(p);
            vm->updateText(text);
        };
        typedef void(*NormalFuncType)(char *, size_t);
        NormalFuncType noCaptureLambdaPtr = noCapture;

        jtn_test2(thread, (void *) noCaptureLambdaPtr, (size_t)this);

        // TODO: Fix app crash
//        graal_tear_down_isolate(thread);
    }

signals:
    void textChanged(const QString &newText);

private:
    QString m_text = "";

    void updateText(const QString &newText) {
        m_text = newText;
        emit textChanged(m_text);
    }
};


#endif // KOTLINJTNVM_H
