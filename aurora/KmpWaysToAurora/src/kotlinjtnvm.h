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
        const char * text;
    } testTwoRes;

    Q_OBJECT
    Q_PROPERTY(QString text MEMBER m_text NOTIFY textChanged)

public:
    explicit KotlinJtnVM(QObject *parent = nullptr): QObject(parent) { qDebug(); };
    ~KotlinJtnVM() { qDebug(); }

public slots:
    void platform_foo() {

        graal_isolate_t *isolate = NULL;
        graal_isolatethread_t *thread = NULL;

        if (graal_create_isolate(NULL, &isolate, &thread) != 0) {
            qDebug() << stderr << "initialization error\n";
        }

        updateText((char *)platform(thread));

        graal_tear_down_isolate(thread);
    }

    void test2_foo() {
        auto noCapture = [](char * text, size_t p) {
            auto vm = reinterpret_cast<KotlinJtnVM *>(p);
            vm->updateText(text);
        };
        typedef void(*NormalFuncType)(char *, size_t);
        NormalFuncType noCaptureLambdaPtr = noCapture;

        graal_isolate_t *isolate = NULL;
        graal_isolatethread_t *thread = NULL;

        if (graal_create_isolate(NULL, &isolate, &thread) != 0) {
            qDebug() << stderr << "initialization error\n";
        }

        test2(thread, (void *) noCaptureLambdaPtr, (size_t)this);

        graal_tear_down_isolate(thread);
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
