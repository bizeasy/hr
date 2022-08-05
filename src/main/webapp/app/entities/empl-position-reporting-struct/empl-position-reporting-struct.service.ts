import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmplPositionReportingStruct } from 'app/shared/model/empl-position-reporting-struct.model';

type EntityResponseType = HttpResponse<IEmplPositionReportingStruct>;
type EntityArrayResponseType = HttpResponse<IEmplPositionReportingStruct[]>;

@Injectable({ providedIn: 'root' })
export class EmplPositionReportingStructService {
  public resourceUrl = SERVER_API_URL + 'api/empl-position-reporting-structs';

  constructor(protected http: HttpClient) {}

  create(emplPositionReportingStruct: IEmplPositionReportingStruct): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emplPositionReportingStruct);
    return this.http
      .post<IEmplPositionReportingStruct>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emplPositionReportingStruct: IEmplPositionReportingStruct): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emplPositionReportingStruct);
    return this.http
      .put<IEmplPositionReportingStruct>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmplPositionReportingStruct>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmplPositionReportingStruct[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(emplPositionReportingStruct: IEmplPositionReportingStruct): IEmplPositionReportingStruct {
    const copy: IEmplPositionReportingStruct = Object.assign({}, emplPositionReportingStruct, {
      fromDate:
        emplPositionReportingStruct.fromDate && emplPositionReportingStruct.fromDate.isValid()
          ? emplPositionReportingStruct.fromDate.format(DATE_FORMAT)
          : undefined,
      thruDate:
        emplPositionReportingStruct.thruDate && emplPositionReportingStruct.thruDate.isValid()
          ? emplPositionReportingStruct.thruDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.thruDate = res.body.thruDate ? moment(res.body.thruDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((emplPositionReportingStruct: IEmplPositionReportingStruct) => {
        emplPositionReportingStruct.fromDate = emplPositionReportingStruct.fromDate
          ? moment(emplPositionReportingStruct.fromDate)
          : undefined;
        emplPositionReportingStruct.thruDate = emplPositionReportingStruct.thruDate
          ? moment(emplPositionReportingStruct.thruDate)
          : undefined;
      });
    }
    return res;
  }
}
