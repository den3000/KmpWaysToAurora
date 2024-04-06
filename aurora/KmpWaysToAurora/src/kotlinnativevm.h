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
        const char * ktText = libshared_symbols()->kotlin.root.platform();
        QString str = QString(ktText);
        return QString("Hello, %1").arg(str);
    }

//    Q_INVOKABLE void next() { emit nextPressed(m_counter); }

signals:
//    void counterChanged(int counter);
//    void nextPressed(int);

public slots:
//    void decreased(int counter) { setCounter(counter); }
};

#endif // KOTLINNATIVEVM_H
