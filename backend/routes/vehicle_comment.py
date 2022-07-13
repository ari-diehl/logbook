from services.vehicle_comment import vehicle_comment_service
from schemas.vehicle_comment import VehicleCommentResponse, VehicleCommentCreate, VehicleCommentUpdate
from database import get_db
from sqlalchemy.orm import Session
from fastapi import APIRouter, Depends, HTTPException

router = APIRouter(prefix="/vehicle_comments")


@router.get("/", response_model=list[VehicleCommentResponse])
def read_vehicle_comments(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    return vehicle_comment_service.read_multi(db, skip, limit)


@router.get("/{comment_id}", response_model=VehicleCommentResponse)
def read_vehicle_comment(comment_id: int, db: Session = Depends(get_db)):
    vehicle_comment = vehicle_comment_service.read(db, comment_id)

    if not vehicle_comment:
        raise HTTPException(status_code=404)

    return vehicle_comment


@router.post("/", response_model=VehicleCommentResponse)
def create_vehicle_comment(vehicle_comment_create: VehicleCommentCreate, db: Session = Depends(get_db)):
    return vehicle_comment_service.create(db, vehicle_comment_create)


@router.put("/{comment_id}", response_model=VehicleCommentResponse)
def update_vehicle_comment(comment_id: int, vehicle_comment_update: VehicleCommentUpdate, db: Session = Depends(get_db)):
    vehicle_comment = vehicle_comment_service.read(db, comment_id)

    if not vehicle_comment:
        raise HTTPException(status_code=404)

    return vehicle_comment_service.update(db, vehicle_comment_update)


@router.delete("/{comment_id}", response_model=VehicleCommentResponse)
def delete_vehicle(comment_id: int, db: Session = Depends(get_db)):
    vehicle_comment = vehicle_comment_service.read(db, comment_id)

    if not vehicle_comment:
        raise HTTPException(status_code=404)

    return vehicle_comment_service.delete(db, comment_id)
