import sqlite3

from fastapi import APIRouter, HTTPException
from depends.hash_user import check_password, hash_password

from models.user_schema import User, UserSignIn, UserEmail, Username
from utils.auth_utils import create_jwt_token

con = sqlite3.connect('users.db')

cur = con.cursor()

user_router = APIRouter(
    tags=["User"],
)


@user_router.post("/email")
async def check_email(data: UserEmail) -> dict:
    user_email = data.email
    cur.execute('''SELECT * FROM user_list WHERE EMAIL=?''', (user_email,))

    find_user = cur.fetchall()

    if find_user:
        raise HTTPException(
            status_code=404,
            detail="이미 존재하는 이메일입니다."
        )
    else:
        return {
            "message": "사용 가능한 이메일입니다."
        }


@user_router.post("/username")
async def check_username(data: Username) -> dict:
    user_username = data.username
    cur.execute('''SELECT * FROM user_list WHERE USERNAME=?''',
                (user_username,))

    find_user = cur.fetchall()

    if find_user:
        raise HTTPException(
            status_code=404,
            detail="이미 존재하는 닉네임입니다."
        )
    else:
        return {
            "message": "사용 가능한 닉네임입니다."
        }


@user_router.post("/signup")
async def sign_new_user(data: User) -> dict:
    user_email = data.email
    user_password = data.password
    user_username = data.username
    hashed_password = hash_password(user_password)

    cur.execute("INSERT INTO user_list (EMAIL, PASSWORD, USERNAME) VALUES(?, ?, ?)",
                (user_email, hashed_password, user_username))
    con.commit()

    return {
        "message": "회원가입이 완료되었습니다."
    }


@user_router.post("/signin")
async def sign_user_in(user: UserSignIn) -> dict:
    user_email = user.email
    user_password = user.password

    cur.execute('''SELECT * FROM user_list WHERE EMAIL=?''', (user_email,))

    find_user = cur.fetchall()

    if not find_user:
        raise HTTPException(
            status_code=404,
            detail="이메일을 다시 확인해주세요."
        )

    find_user_password = find_user[0][2]
    find_username = find_user[0][3]

    if not check_password(input_password=user_password, hashed_password=find_user_password):
        raise HTTPException(
            status_code=404,
            detail="비밀번호를 다시 확인해주세요."
        )

    token_data = dict(email=user_email, username=find_username)

    access_token = create_jwt_token(token_data, 60)

    return {"message": "로그인이 완료되었습니다.", "username": find_username, "access_token": access_token}
