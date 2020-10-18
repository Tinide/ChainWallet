/**
 * Copyright (c) 2013-2014 Tomas Dzetkulic
 * Copyright (c) 2013-2014 Pavol Rusnak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

#ifndef __SECP256K1_H__
#define __SECP256K1_H__

#include <stdint.h>
#include "src/ecdsa.h"

extern const ecdsa_curve secp256k1;

void secp256k1_get_public(const uint8_t *priv_key, uint8_t *pub_key, int isCompress);

int secp256k1_sign(const uint8_t *priv_key, const uint8_t *digest, uint8_t *sig, uint8_t *pby);

int secp256k1_eth_sign(const uint8_t *priv_key, const uint8_t *digest, uint8_t *sig, uint8_t *pby);

int secp256k1_verify(const uint8_t *pub_key, const uint8_t *sig, const uint8_t *digest);

#endif
