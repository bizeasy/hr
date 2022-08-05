import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacilityUsageLog } from 'app/shared/model/facility-usage-log.model';

type EntityResponseType = HttpResponse<IFacilityUsageLog>;
type EntityArrayResponseType = HttpResponse<IFacilityUsageLog[]>;

@Injectable({ providedIn: 'root' })
export class FacilityUsageLogService {
  public resourceUrl = SERVER_API_URL + 'api/facility-usage-logs';

  constructor(protected http: HttpClient) {}

  create(facilityUsageLog: IFacilityUsageLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facilityUsageLog);
    return this.http
      .post<IFacilityUsageLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(facilityUsageLog: IFacilityUsageLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facilityUsageLog);
    return this.http
      .put<IFacilityUsageLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFacilityUsageLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFacilityUsageLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(facilityUsageLog: IFacilityUsageLog): IFacilityUsageLog {
    const copy: IFacilityUsageLog = Object.assign({}, facilityUsageLog, {
      fromTime: facilityUsageLog.fromTime && facilityUsageLog.fromTime.isValid() ? facilityUsageLog.fromTime.toJSON() : undefined,
      toTime: facilityUsageLog.toTime && facilityUsageLog.toTime.isValid() ? facilityUsageLog.toTime.toJSON() : undefined,
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
      res.body.forEach((facilityUsageLog: IFacilityUsageLog) => {
        facilityUsageLog.fromTime = facilityUsageLog.fromTime ? moment(facilityUsageLog.fromTime) : undefined;
        facilityUsageLog.toTime = facilityUsageLog.toTime ? moment(facilityUsageLog.toTime) : undefined;
      });
    }
    return res;
  }
}
