import bcrypt

def hash_password(password:str):
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
    return hashed_password


def check_password(input_password:str, hashed_password:str):
    return bcrypt.checkpw(input_password.encode('utf-8'), hashed_password)