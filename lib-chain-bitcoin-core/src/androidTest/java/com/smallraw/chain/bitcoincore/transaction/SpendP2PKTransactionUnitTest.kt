package com.smallraw.chain.bitcoincore.transaction

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smallraw.chain.bitcoincore.PrivateKey
import com.smallraw.chain.bitcoincore.addressConvert.AddressConverter
import com.smallraw.chain.bitcoincore.network.TestNet
import com.smallraw.chain.bitcoincore.script.Chunk
import com.smallraw.chain.bitcoincore.script.ChunkData
import com.smallraw.chain.bitcoincore.script.OP_CHECKSIG
import com.smallraw.chain.bitcoincore.script.Script
import com.smallraw.chain.bitcoincore.transaction.serializers.TransactionSerializer
import com.smallraw.chain.lib.core.extensions.hexToByteArray
import com.smallraw.chain.lib.core.extensions.toHex
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * ## 花费 P2PK 的 UTXO ##
 *
 * 签字获取 Hash 时对应的 UTXO 输入的脚本中放入锁定脚本
 * <UTXO 持有者的公钥> OP_CHECKSIG
 *
 * 使用 UTXO 对应的持有者私钥对交易 Hash 签字获得签名
 *
 * 对应的 UTXO 输入的脚本中放入解锁脚本
 * <签名>
 *
 *
 *
 * ## 支付到 P2PK ##
 *
 * 在交易输出中填写锁定脚本
 * <UTXO 持有者的公钥> OP_CHECKSIG
 */
@RunWith(AndroidJUnit4::class)
class SpendP2PKTransactionUnitTest {

    @Test
    fun spend_p2sh_to_p2pkh() {
        val network = TestNet()

        val convert = AddressConverter.default(network)

        val p2pkPrivateKey =
            PrivateKey("81c70e36ffa5e3e6425dc19c7c35315d3d72dc60b79cb78fe009a335de29dd22".hexToByteArray())
        val p2pkPublicKey = p2pkPrivateKey.getPublicKey()

        val txin =
            Transaction.Input(
                "7db363d5a7fabb64ccce154e906588f1936f34481223ea8c1f2c935b0a0c945b".hexToByteArray(),
                0
            )
        val redeemScript = Script(ChunkData { p2pkPublicKey.getKey() }, Chunk { OP_CHECKSIG })

        val toAddr = convert.convert("n4bkvTyU1dVdzsrhWBqBw8fEMbHjJvtmJR")
        val txout = Transaction.Output(8000000, toAddr.lockScript())

        val tx = Transaction(arrayOf(txin), arrayOf(txout))

        Log.e(
            "TransactionUnitTest",
            "\nRaw unsigned transaction:\n" + TransactionSerializer.serialize(tx).toHex()
        )

        val txDigest = TransactionSerializer.hashForSignature(tx, 0, redeemScript)
        val sig = p2pkPrivateKey.sign(txDigest)
//        txin.script = Script(ChunkData { sig.signature() } + redeemScript.chunks)
        txin.script = Script(ChunkData { sig.signature() })

        Log.e(
            "TransactionUnitTest",
            "\nRaw signed transaction:\n" + TransactionSerializer.serialize(tx).toHex()
        )

        Assert.assertArrayEquals(
            TransactionSerializer.serialize(tx, false),
            "02000000015b940c0a5b932c1f8cea231248346f93f18865904e15cecc64bbfaa7d563b37d0000000000ffffffff0100127a00000000001976a914fd337ad3bf81e086d96a68e1f8d6a0a510f8c24a88ac00000000".hexToByteArray()
        )

        Assert.assertArrayEquals(
            TransactionSerializer.serialize(tx),
            "02000000015b940c0a5b932c1f8cea231248346f93f18865904e15cecc64bbfaa7d563b37d000000004847304402204984c2089bf55d5e24851520ea43c431b0d79f90d464359899f27fb40a11fbd302201cc2099bfdc18c3a412afb2ef1625abad8a2c6b6ae0bf35887b787269a6f2d4d01ffffffff0100127a00000000001976a914fd337ad3bf81e086d96a68e1f8d6a0a510f8c24a88ac00000000".hexToByteArray()
        )
    }
}