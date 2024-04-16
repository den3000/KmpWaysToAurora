#ifndef KOTLINNATIVEVM_H
#define KOTLINNATIVEVM_H

#include <QObject>
#include <QDebug>
#include <QQuickItem>

#include <libshared_api.h>

class KotlinNativeVM : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString text MEMBER m_text NOTIFY textChanged)

public:
    explicit KotlinNativeVM(QObject *parent = nullptr): QObject(parent) { qDebug(); };
    ~KotlinNativeVM() { qDebug(); }

public slots:
    void std() {
        auto ktText = libshared_symbols()->kotlin.root.platform();
        auto ktDataClass = libshared_symbols()->kotlin.root.getDataClass();
        auto ktDataClassStr = libshared_symbols()->kotlin.root.DataClass.toString(ktDataClass);

        updateText(QString("Hello, %1\n%2")
                           .arg(ktText)
                           .arg(ktDataClassStr));
    }

    void serialization() {
        updateText("serialization");
    }

    void coroutines() {
        updateText("coroutines");
    }

    void ktor() {
        updateText("ktor");
    }

    void db() {
        updateText("db");
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

#endif // KOTLINNATIVEVM_H
