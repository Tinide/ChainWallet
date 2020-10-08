package com.smallraw.chain.bitcoincore.crypto

import com.smallraw.chain.lib.Signature
import com.smallraw.chain.lib.crypto.Secp256K1
import com.smallraw.chain.lib.execptions.JNICallException
import java.security.PrivateKey

class Secp256k1Signer {
    @Throws(JNICallException::class)
    fun sign(privateKey: PrivateKey, message: ByteArray): Signature {
        return Signature(Secp256K1.sign(privateKey.encoded, message))
    }

    @Throws(JNICallException::class)
    fun verify(publicKey: ByteArray, signature: ByteArray, message: ByteArray): Boolean {
        return Secp256K1.verify(publicKey, signature, message)
    }
}