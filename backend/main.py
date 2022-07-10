from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from uvicorn import run

from database import Base, engine
from routes.employee import router as employee_router
from routes.auth import router as auth_router
from routes.vehicle import router as vehicle_router
from routes.trip import router as trip_router

Base.metadata.create_all(engine, checkfirst=True)

app = FastAPI(title="Logbook REST-API")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(employee_router)
app.include_router(auth_router)
app.include_router(vehicle_router)
app.include_router(trip_router)

if __name__ == "__main__":
    run(app, host="0.0.0.0", port=5000)
