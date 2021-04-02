# ============= Algorithm ===============
# 1. Select largest prime number p & q
# 2. calculate n = p * q
# 3. calculate phi = (p-1)(q-1)
# 4. choose value of e so that 1<e<phi and e,phi are co prime numbers that is
# they must have one common factor of e and phi is 1
# calculate d using (e * d) = 1 % phi
# C = P^e % n
# P = C^d % n , P must be less than n in RSA

# #### My code can work with message of length 3 ####

# import
from random import random, randint
import math

# variables
alphabet_code = {
    'A': '10',
    'B': '11',
    'C': '12',
    'D': '13',
    'E': '14',
    'F': '15',
    'G': '16',
    'H': '17',
    'I': '18',
    'J': '19',
    'K': '20',
    'L': '21',
    'M': '22',
    'N': '23',
    'O': '24',
    'P': '25',
    'Q': '26',
    'R': '27',
    'S': '28',
    'T': '29',
    'U': '30',
    'V': '31',
    'W': '32',
    'X': '33',
    'Y': '34',
    'Z': '35',
}
max_lim = 999
p = 0
q = 0
n = 0
phi = 0
e = 0   # public key
d = 0   # private key
message = "DCM"
P = ""
C = ""
have_a = False
first_pos_zero = False


# methods
def __find_largest_prime_number__():
    while True:
        flag = False
        rand = randint(10, max_lim)
        for i in range(2, rand):
            if (rand % i) == 0:
                flag = True
                break
        if not flag:
            return rand


def __gcd__(a, b):
    while b != 0:
        a, b = b, a % b
    return a


def __is_coprime__(x, y):
    return __gcd__(x, y) == 1


def __select_e__(phi):
    while True:
        temp_e = randint(2, phi)
        if not __is_coprime__(temp_e, phi):
            continue
        else:
            return temp_e

def __check_ed_equation__(x, y, phi, e):
    if(phi*x + e*y) == 1:
        # print(y)
        if y > phi:
            y = y % phi
        if y < 0:
            y = y + phi
        return y

def __find_d__(e, phi):
    # (e * d) % phi = 1
    # de = 1 (mod phi)
    # de + k*phi = 1
    # so, de + k*phi = r, where r = gcd(e, phi)
    # lets make 4 column a, b, d, k
    ar = [[1, 0, phi, '-'], [0, 1, e, (phi // e)]]
    i = 2   # because have 2 entries already
    while True:
        col = []
        col.append(ar[i-2][0]-(ar[i-1][0]*ar[i-1][3]))
        col.append(ar[i-2][1]-(ar[i-1][1]*ar[i-1][3]))
        col.append(ar[i-2][2]-(ar[i-1][2]*ar[i-1][3]))
        col.append((ar[i-1][2] // col[2]))
        ar.append(col)
        if ar[i][2] == 1:
            ar[i][3] = '-'
            break
        else:
            i = i + 1
            continue
    return __check_ed_equation__(ar[i][0], ar[i][1], phi, e)


# def __euclidean_gcd__(a, b):
#     if a == 0:
#         return (b, 0, 1)
#     else:
#         g, y, x = __euclidean_gcd__(b % a, a)
#         return (g, x - (b // a) * y, y)
#
#
# def __modinv__(e, phi):
#     g, x, y = __euclidean_gcd__(e, phi)
#     if g!= 1:
#         return -1
#     else:
#         return x % phi
#
#
# def __find_d__(e, phi):
#     return __modinv__(e, phi)


def __find_phi__(_p, _q):
    return (_p-1) * (_q-1)


def __find_n__(_p, _q, plain_text):
    while True:
        _n = _p * _q
        if _n < plain_text:
            # print(_n)
            global p
            p = __find_largest_prime_number__()
            _p = p
            global q
            q = __find_largest_prime_number__()
            _q = q
            continue
        else:
            return _n


def __find_P__(msg, alpha_code):
    temp_p = ""
    for c in msg:
        if c in alpha_code:
            temp_p += alpha_code[c]
    return temp_p


def __get_key__(val, alpha_code):
    for key, value in alpha_code.items():
         if val == value:
             return key


def __convert_decrypted_number_into_text__(recover_text, alphabet_code):
    for i in range(0, len(recover_text), 2):
        char_num = recover_text[i:i + 2]
        key = __get_key__(str(char_num), alphabet_code)
        print(key, end="")
    print()


# encryption
p = __find_largest_prime_number__()
q = __find_largest_prime_number__()

P = __find_P__(message, alphabet_code)
n = __find_n__(p, q, int(P))
phi = __find_phi__(p, q)
e = __select_e__(phi)
d = __find_d__(e, phi)


# if(e * d) % phi == 1:
#     print("satisfied")

# check first number is zero that is A to J. if A then append two zero otherwise one
# if 'A' in message[0]:
#     have_a = True
#
# for i in ['B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']:
#     if i in message[0]:
#         first_pos_zero = True
#         break


# cipher text
C = str((int(P) ** e) % n)

# decrypt with private key d
recover_text = str((int(C) ** d) % n)

# print all
print("p: " + str(p))
print("q: " + str(q))
print("n: " + str(n))
print("Phi: " + str(phi))
print("e: " + str(e))
print("d: " + str(d))
print("plain text: " + P)
print("cipher(encrypted) text: " + C)
print("decrypted text: " + recover_text)

__convert_decrypted_number_into_text__(recover_text, alphabet_code)

