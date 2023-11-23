import sqlite3

from fastapi import APIRouter, HTTPException, Header
from dong_api.users import User
from models.user_schema import UserSignUp, UserSignIn


con = sqlite3.connect('users.db')
cur = con.cursor()

user_router = APIRouter(
    tags=["User"],
)

user = User(db_name="users.db", db_table_name="user_list")


def get_token(authorization: str = Header(...)):
    if authorization is None:
        raise HTTPException(status_code=401, detail="인증되지 않은 사용자입니다.")
    return authorization


@user_router.post("/signup")
async def sign_new_user(data: UserSignUp) -> dict:
    response = user.signupUser(data)
    if not (response["result"]):
        error_detail = response.get("message", "Unknown error")
        raise HTTPException(
            status_code=404,
            detail=error_detail,
        )
    else:
        return response


@user_router.post("/signin")
async def sign_user_in(data: UserSignIn) -> dict:
    response = user.signinUser(data) # db 조회
    if not (response["result"]):
        error_detail = response.get("message", "Unknown error")
        raise HTTPException(
            status_code=404,
            detail=error_detail,
        )
    else:
        return response


# @user_router.get("/myname")
# async def get_data(user_token: str = Depends(get_token)) -> Username:

#     token = user_token.split("Bearer ")[1]
#     user_info = decode_jwt_token(token)
#     username = user_info["data"]["username"]

#     return {"username": username}
