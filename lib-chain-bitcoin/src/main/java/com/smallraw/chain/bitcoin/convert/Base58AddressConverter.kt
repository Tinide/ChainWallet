package com.smallraw.chain.bitcoin.convert

import com.smallraw.chain.bitcoin.Bitcoin
import com.smallraw.chain.bitcoin.execptions.AddressFormatException
import com.smallraw.chain.bitcoin.transaction.script.ScriptType
import com.smallraw.chain.lib.core.crypto.Base58

class Base58AddressConverter(
    private val addressVersion: Int,
    private val addressScriptVersion: Int
) : IAddressConverter {

    override fun convert(addressString: String): Bitcoin.Address {
        val data = Base58.decodeCheck(addressString)
        if (data.size != 20 + 1) {
            throw AddressFormatException("Address length is not 20 hash")
        }

        val bytes = data.copyOfRange(1, data.size)
        val addressType = when (data[0].toInt() and 0xFF) {
            addressScriptVersion -> Bitcoin.Address.AddressType.P2SH
            addressVersion -> Bitcoin.Address.AddressType.P2PKH
            else -> throw AddressFormatException("Wrong address prefix")
        }

        return Bitcoin.LegacyAddress(addressString, bytes, addressType)
    }

    override fun convert(hash160Bytes: ByteArray, scriptType: ScriptType): Bitcoin.Address {
        val addressType: Bitcoin.Address.AddressType
        val addressVersion: Int

        when (scriptType) {
            ScriptType.P2PK,
            ScriptType.P2PKH -> {
                addressType = Bitcoin.Address.AddressType.P2PKH
                addressVersion = this.addressVersion
            }
            ScriptType.P2SH,
            ScriptType.P2WPKHSH -> {
                addressType = Bitcoin.Address.AddressType.P2SH
                addressVersion = addressScriptVersion
            }

            else -> throw AddressFormatException("Unknown Address Type")
        }

        val addressBytes = byteArrayOf(addressVersion.toByte()) + hash160Bytes

        val addressString = Base58.encodeCheck(addressBytes)

        return Bitcoin.LegacyAddress(addressString, hash160Bytes, addressType)
    }

    override fun convert(publicKey: Bitcoin.PublicKey, scriptType: ScriptType): Bitcoin.Address {
        var keyhash = publicKey.getHash()

        if (scriptType == ScriptType.P2WPKHSH) {
            keyhash = publicKey.scriptHashP2WPKH()
        }

        return convert(keyhash, scriptType)
    }
}
