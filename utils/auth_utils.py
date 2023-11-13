import os
import jwt

from datetime import datetime, timedelta
from dotenv import load_dotenv

load_dotenv()
SECRET_KEY = os.getenv("SECRET_KEY")
ALGORITHM = "HS256"


def create_jwt_token(data: dict, delta: int):
    to_encode = data.copy()
    expire = datetime.utcnow() + timedelta(minutes=delta)
    payload = dict(exp=expire, data=to_encode)
    encode_jwt = jwt.encode(payload, SECRET_KEY, algorithm=ALGORITHM)

    return encode_jwt


def decode_jwt_token(token: str):
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=ALGORITHM)
        return payload
    except jwt.ExpiredSignatureError:
        raise Exception("토큰이 만료되었습니다.")
    except jwt.InvalidTokenError:
        raise Exception("토큰이 유효하지 않습니다.")
