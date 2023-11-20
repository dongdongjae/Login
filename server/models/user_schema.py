from dong_api.model_user import UserEmail, Username, SigninUser, SignupUser


class UserEmail(UserEmail):

    class Config:
        json_schema_extra = {
            "example": {
                "email": "test@naver.com",
            }
        }


class Username(Username):

    class Config:
        json_schema_extra = {
            "example": {
                "username": "도라에몽"
            }
        }


class UserSignUp(SignupUser):

    class Config:
        json_schema_extra = {
            "example": {
                "email": "test@naver.com",
                "password": "test123!",
                "username": "도라에몽"
            }
        }


class UserSignIn(SigninUser):

    class Config:
        json_schema_extra = {
            "example": {
                "email": "test@naver.com",
                "password": "test123!",
            }
        }


class Username(Username):

    class Config:
        json_schema_extra = {
            "example": {
                "username": "도라에몽"
            }
        }
