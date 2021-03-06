package com.smallraw.crypto.core.jni

class CryptoJNI {
    companion object {
        init {
            System.loadLibrary("crypto-wrapper")
        }
    }

    external fun hexToStr(date: ByteArray, dataSize: Int = date.size): String?
    external fun strToHex(date: String): ByteArray?

    external fun base58Encode(date: ByteArray, dataSize: Int = date.size): String?
    external fun base58Decode(date: String): ByteArray?
    external fun base58EncodeCheck(date: ByteArray, dataSize: Int = date.size): String?
    external fun base58DecodeCheck(date: String): ByteArray?

    external fun sha256(date: ByteArray, dataSize: Int = date.size): ByteArray?
    external fun doubleSha256(date: ByteArray, dataSize: Int = date.size): ByteArray?

    external fun ripemd160(date: ByteArray, dataSize: Int = date.size): ByteArray?

    external fun sha3_256(date: ByteArray, dataSize: Int = date.size): ByteArray?
    external fun doubleSha3_256(date: ByteArray, dataSize: Int = date.size): ByteArray?

    external fun keccak_256(date: ByteArray, dataSize: Int = date.size): ByteArray?
    external fun doubleKeccak_256(date: ByteArray, dataSize: Int = date.size): ByteArray?

    external fun hmac_sha256(
        key: ByteArray,
        message: ByteArray,
        keySize: Int = key.size,
        messageSize: Int = message.size
    ): ByteArray?

    external fun hmac_sha512(
        key: ByteArray,
        message: ByteArray,
        keySize: Int = key.size,
        messageSize: Int = message.size
    ): ByteArray?

    external fun sig_to_der(
        sign: ByteArray,
        signSize: Int = sign.size
    ): ByteArray?

    external fun der_to_sig(
        der: ByteArray,
        derSize: Int = der.size
    ): ByteArray?
}