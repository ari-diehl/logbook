from sqlalchemy.orm import Session
from typing import Any, Dict, Optional, TypeVar, Union, Type, Generic
from pydantic import BaseModel
from fastapi.encoders import jsonable_encoder

from database import Base

ModelType = TypeVar("ModelType", bound=Base)
CreateSchemaType = TypeVar("CreateSchemaType", bound=BaseModel)
UpdateSchemaType = TypeVar("UpdateSchemaType", bound=BaseModel)


class BaseService(Generic[ModelType, CreateSchemaType, UpdateSchemaType]):
    def __init__(self, model: Type[ModelType]):
        self.model = model

    def read_multi(self, db: Session, skip: int = 0, limit: int = 100) -> list[ModelType]:
        return db.query(self.model).offset(skip).limit(limit).all()

    def read(self, db: Session, id: Any) -> Optional[ModelType]:
        return db.query(self.model).get(id)

    def create(self, db: Session, obj_create: CreateSchemaType) -> ModelType:
        obj_create_data = jsonable_encoder(obj_create)

        obj_db = self.model(**obj_create_data)

        db.add(obj_db)
        db.commit()
        db.refresh(obj_db)

        return obj_db

    def update(
        self,
        db: Session,
        obj_db: ModelType,
        obj_update: Union[UpdateSchemaType, Dict[str, Any]]
    ) -> ModelType:
        obj_data = jsonable_encoder(obj_db)

        if isinstance(obj_update, dict):
            update_data = obj_update
        else:
            update_data = obj_update.dict(exclude_unset=True)

        for field in obj_data:
            if field in update_data:
                setattr(obj_db, field, update_data[field])

        db.add(obj_db)
        db.commit()
        db.refresh(obj_db)

        return obj_db

    def delete(self, db: Session, id: Any) -> ModelType:
        obj_db = db.query(self.model).get(id)

        db.delete(obj_db)
        db.commit()

        return obj_db
