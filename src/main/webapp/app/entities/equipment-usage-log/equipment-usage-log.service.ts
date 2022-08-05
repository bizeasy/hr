import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEquipmentUsageLog } from 'app/shared/model/equipment-usage-log.model';

type EntityResponseType = HttpResponse<IEquipmentUsageLog>;
type EntityArrayResponseType = HttpResponse<IEquipmentUsageLog[]>;

@Injectable({ providedIn: 'root' })
export class EquipmentUsageLogService {
  public resourceUrl = SERVER_API_URL + 'api/equipment-usage-logs';

  constructor(protected http: HttpClient) {}

  create(equipmentUsageLog: IEquipmentUsageLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(equipmentUsageLog);
    return this.http
      .post<IEquipmentUsageLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(equipmentUsageLog: IEquipmentUsageLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(equipmentUsageLog);
    return this.http
      .put<IEquipmentUsageLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEquipmentUsageLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEquipmentUsageLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(equipmentUsageLog: IEquipmentUsageLog): IEquipmentUsageLog {
    const copy: IEquipmentUsageLog = Object.assign({}, equipmentUsageLog, {
      fromTime: equipmentUsageLog.fromTime && equipmentUsageLog.fromTime.isValid() ? equipmentUsageLog.fromTime.toJSON() : undefined,
      toTime: equipmentUsageLog.toTime && equipmentUsageLog.toTime.isValid() ? equipmentUsageLog.toTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromTime = res.body.fromTime ? moment(res.body.fromTime) : undefined;
      res.body.toTime = res.body.toTime ? moment(res.body.toTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((equipmentUsageLog: IEquipmentUsageLog) => {
        equipmentUsageLog.fromTime = equipmentUsageLog.fromTime ? moment(equipmentUsageLog.fromTime) : undefined;
        equipmentUsageLog.toTime = equipmentUsageLog.toTime ? moment(equipmentUsageLog.toTime) : undefined;
      });
    }
    return res;
  }
}
