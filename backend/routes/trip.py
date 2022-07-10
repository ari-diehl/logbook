from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from database import get_db
from schemas.trip import TripResponse, TripCreate, TripUpdate
from services.trip import trip_service

router = APIRouter(prefix="/trips")


@router.get("/", response_model=list[TripResponse])
def read_all(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    return trip_service.read_multi(db, skip, limit)


@router.get("/{trip_id}", response_model=TripResponse)
def read(trip_id: int, db: Session = Depends(get_db)):
    trip = trip_service.read(db, trip_id)

    if not trip:
        raise HTTPException(status_code=404)

    return trip


@router.post("/", response_model=TripResponse)
def create(trip_create: TripCreate, db: Session = Depends(get_db)):
    if trip_service.read(db, trip_create.id):
        raise HTTPException(
            status_code=400)

    return trip_service.create(db, trip_create)


@router.put("/", response_model=TripResponse)
def update(trip_update: TripUpdate, db: Session = Depends(get_db)):
    trip = trip_service.read(db, trip_update.id)

    if not trip:
        raise HTTPException(status_code=404)

    return trip_service.update(db, trip, trip_update)


@router.delete("/{trip_id}", response_model=TripResponse)
def delete(trip_id: int, db: Session = Depends(get_db)):
    trip = trip_service.read(db, trip_id)

    if not trip:
        raise HTTPException(status_code=404)

    return trip_service.delete(db, trip_id)
