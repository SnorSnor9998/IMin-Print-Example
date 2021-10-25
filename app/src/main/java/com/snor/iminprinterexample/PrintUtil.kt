package com.snor.iminprinterexample

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import com.imin.printerlib.IminPrintUtils

class PrintUtil(private val context : Context) {

    private val TAG = "SPE"

    private val printer : IminPrintUtils = IminPrintUtils.getInstance(context)
    private var connectType : IminPrintUtils.PrintConnectType = IminPrintUtils.PrintConnectType.USB


    init {

        printer.resetDevice()
        printer.initPrinter(connectType)

        when (printer.getPrinterStatus(connectType)) {
            -1 -> Log.d(TAG,"PRINTER STATUS => The printer is not connected or powered on")
            0 -> Log.d(TAG,"PRINTER STATUS => The printer is normal")
            1 -> Log.d(TAG,"PRINTER STATUS => The printer is not connected or powered on")
            3 -> Log.d(TAG,"PRINTER STATUS => Print head open")
            7 -> Log.d(TAG,"PRINTER STATUS => No Paper Feed")
            8 -> Log.d(TAG,"PRINTER STATUS => Paper Running Out")
            99 -> Log.d(TAG,"PRINTER STATUS => Other errors")
        }
    }

    // Printer Config
    fun setConnectType(type: IminPrintUtils.PrintConnectType){
        this.connectType = type
    }

    fun setPaperSize(size: PaperSize){
        printer.setPageFormat(size.ordinal)
    }


    fun printExample(){


        drawPicture(R.drawable.food)
        feedPaper(25)

        setFontStyle(FontStyle.BOLD)
        drawText(TextSize.SizeM,TextAlign.CENTER,"SnorSnor")
        feedPaper(25)

        setFontStyle(FontStyle.NORMAL)
        drawText(TextSize.SizeS,"Date: 31 Dec 2021 23:59:59")
        drawText(TextSize.SizeS,"MID: 1615881531729644881")
        drawText(TextSize.SizeS,"TID: 21100156855430123276880001")
        feedPaper(25)

        setFontStyle(FontStyle.BOLD)
        drawText(TextSize.SizeL,TextAlign.CENTER,"SALE")


        drawTable3(arrayOf("Mc Chicken","3","12.99"))
        drawTable3(arrayOf("Mc Nugget","1","10.99"))
        drawTable3(arrayOf("Double GCB","2","14.99"))
        drawTable2(arrayOf("Total","79.94"))

        drawQR("https://docs.imin.sg/docs/en/PrinterSDK.html")

        printBarcode(BarcodeType.CODE39,"123456789")


        feedPaper(100)
        cutPaper()

    }



    // Print Function
    private fun drawText(size: TextSize, text: String) {
        printer.setTextSize(size.value)
        printer.setAlignment(TextAlign.LEFT.ordinal)
        printer.printText("$text.\n")
    }

    private fun drawText(size: TextSize, align: TextAlign, text: String) {
        printer.setTextSize(size.value)
        printer.setAlignment(align.ordinal)
        printer.printText("$text.\n")
    }

    private fun drawQR(data:String){
        printer.printQrCode(data,TextAlign.CENTER.ordinal)
    }

    private fun drawPicture(id:Int){
        val pic = BitmapFactory.decodeResource(context.resources,id)
        if (pic != null){
            printer.printSingleBitmap(pic,TextAlign.CENTER.ordinal)
        }
    }

    private fun cutPaper(){
        printer.partialCut()
    }


    private fun drawTable3(text: Array<String>) {
        printer.printColumnsText(
            text,
            intArrayOf(2,1,1),
            intArrayOf(TextAlign.LEFT.ordinal,TextAlign.RIGHT.ordinal,TextAlign.RIGHT.ordinal),
            intArrayOf(TextSize.SizeM.value,TextSize.SizeM.value,TextSize.SizeM.value))
    }

    private fun drawTable2(text: Array<String>) {
        printer.printColumnsText(
            text,
            intArrayOf(1,1),
            intArrayOf(TextAlign.LEFT.ordinal,TextAlign.RIGHT.ordinal),
            intArrayOf(TextSize.SizeM.value,TextSize.SizeM.value))
    }


    private fun printBarcode(type: BarcodeType, data: String){
        printer.setBarCodeHeight(100)
        printer.printBarCode(type.value,data,TextAlign.CENTER.ordinal)
    }

    private fun feedPaper(value : Int = 1){
        printer.printAndFeedPaper(value)
    }


    private fun setFontStyle(style: FontStyle){
        printer.setTextStyle(style.ordinal)
    }





}

private enum class TextAlign {
    LEFT,
    CENTER,
    RIGHT
}

private enum class FontStyle{
    NORMAL,
    BOLD,
    ITALIC,
    BOLD_ITALIC
}

private enum class TextSize(val value: Int) {
    SizeXS(18),
    SizeS(24),
    SizeM(30),
    SizeL(46),
    SizeXL(110)
}

private enum class BarcodeType(val value: Int){
    UPC_A(0),
    UPC_E(1),
    JAN13(2),
    EAN13(2),
    JAN8(3),
    EAN8(3),
    CODE39(4),
    ITF(5),
    CODABAR(6)
}

enum class PaperSize{
    Size80,
    Size58
}