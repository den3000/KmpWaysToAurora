#ifndef KOTLINNATIVEVM_H
#define KOTLINNATIVEVM_H

#include <QObject>
#include <QDebug>
#include <QQuickItem>

#include <libshared_api.h>

class KotlinNativeVM : public QObject
{
    Q_OBJECT

public:
    explicit KotlinNativeVM(QObject *parent = nullptr): QObject(parent) { qDebug(); };
    ~KotlinNativeVM() { qDebug(); }

    Q_INVOKABLE QString text() {
        auto ktText = libshared_symbols()->kotlin.root.platform();
        auto ktDataClass = libshared_symbols()->kotlin.root.getDataClass();
        auto ktDataClassStr = libshared_symbols()->kotlin.root.DataClass.toString(ktDataClass);

        return QString("Hello, %1\n%2")
                .arg(ktText)
                .arg(ktDataClassStr)
                ;
    }

//    Q_INVOKABLE void next() { emit nextPressed(m_counter); }

signals:
//    void counterChanged(int counter);
//    void nextPressed(int);

public slots:
//    void decreased(int counter) { setCounter(counter); }
};

#endif // KOTLINNATIVEVM_H
