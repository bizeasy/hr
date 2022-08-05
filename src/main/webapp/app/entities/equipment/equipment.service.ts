import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEquipment } from 'app/shared/model/equipment.model';

type EntityResponseType = HttpResponse<IEquipment>;
type EntityArrayResponseType = HttpResponse<IEquipment[]>;

@Injectable({ providedIn: 'root' })
export class EquipmentService {
  public resourceUrl = SERVER_API_URL + 'api/equipment';

  constructor(protected http: HttpClient) {}

  create(equipment: IEquipment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(equipment);
    return this.http
      .post<IEquipment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(equipment: IEquipment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(equipment);
    return this.http
      .put<IEquipment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEquipment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEquipment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(equipment: IEquipment): IEquipment {
    const copy: IEquipment = Object.assign({}, equipment, {
      lastCalibratedDate:
        equipment.lastCalibratedDate && equipment.lastCalibratedDate.isValid() ? equipment.lastCalibratedDate.toJSON() : undefined,
      calibrationDueDate:
        equipment.calibrationDueDate && equipment.calibrationDueDate.isValid() ? equipment.calibrationDueDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastCalibratedDate = res.body.lastCalibratedDate ? moment(res.body.lastCalibratedDate) : undefined;
      res.body.calibrationDueDate = res.body.calibrationDueDate ? moment(res.body.calibrationDueDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((equipment: IEquipment) => {
        equipment.lastCalibratedDate = equipment.lastCalibratedDate ? moment(equipment.lastCalibratedDate) : undefined;
        equipment.calibrationDueDate = equipment.calibrationDueDate ? moment(equipment.calibrationDueDate) : undefined;
      });
    }
    return res;
  }
}
