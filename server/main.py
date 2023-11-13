from fastapi import FastAPI

import uvicorn

from routes.users import user_router


app = FastAPI()


@app.get("/")
def home():
    return {"home": "home"}

app.include_router(user_router, prefix="/api/user")


if __name__ == '__main__':
    uvicorn.run("main:app", host="127.0.0.1", port=8000, reload=True)