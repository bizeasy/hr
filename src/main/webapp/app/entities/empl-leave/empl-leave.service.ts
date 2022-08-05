import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmplLeave } from 'app/shared/model/empl-leave.model';

type EntityResponseType = HttpResponse<IEmplLeave>;
type EntityArrayResponseType = HttpResponse<IEmplLeave[]>;

@Injectable({ providedIn: 'root' })
export class EmplLeaveService {
  public resourceUrl = SERVER_API_URL + 'api/empl-leaves';

  constructor(protected http: HttpClient) {}

  create(emplLeave: IEmplLeave): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emplLeave);
    return this.http
      .post<IEmplLeave>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emplLeave: IEmplLeave): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emplLeave);
    return this.http
      .put<IEmplLeave>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmplLeave>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmplLeave[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(emplLeave: IEmplLeave): IEmplLeave {
    const copy: IEmplLeave = Object.assign({}, emplLeave, {
      fromDate: emplLeave.fromDate && emplLeave.fromDate.isValid() ? emplLeave.fromDate.format(DATE_FORMAT) : undefined,
      thruDate: emplLeave.thruDate && emplLeave.thruDate.isValid() ? emplLeave.thruDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((emplLeave: IEmplLeave) => {
        emplLeave.fromDate = emplLeave.fromDate ? moment(emplLeave.fromDate) : undefined;
        emplLeave.thruDate = emplLeave.thruDate ? moment(emplLeave.thruDate) : undefined;
      });
    }
    return res;
  }
}
