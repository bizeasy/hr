import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkEffortAssoc } from 'app/shared/model/work-effort-assoc.model';

type EntityResponseType = HttpResponse<IWorkEffortAssoc>;
type EntityArrayResponseType = HttpResponse<IWorkEffortAssoc[]>;

@Injectable({ providedIn: 'root' })
export class WorkEffortAssocService {
  public resourceUrl = SERVER_API_URL + 'api/work-effort-assocs';

  constructor(protected http: HttpClient) {}

  create(workEffortAssoc: IWorkEffortAssoc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workEffortAssoc);
    return this.http
      .post<IWorkEffortAssoc>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(workEffortAssoc: IWorkEffortAssoc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workEffortAssoc);
    return this.http
      .put<IWorkEffortAssoc>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWorkEffortAssoc>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWorkEffortAssoc[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(workEffortAssoc: IWorkEffortAssoc): IWorkEffortAssoc {
    const copy: IWorkEffortAssoc = Object.assign({}, workEffortAssoc, {
      fromDate: workEffortAssoc.fromDate && workEffortAssoc.fromDate.isValid() ? workEffortAssoc.fromDate.toJSON() : undefined,
      thruDate: workEffortAssoc.thruDate && workEffortAssoc.thruDate.isValid() ? workEffortAssoc.thruDate.toJSON() : undefined,
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
      res.body.forEach((workEffortAssoc: IWorkEffortAssoc) => {
        workEffortAssoc.fromDate = workEffortAssoc.fromDate ? moment(workEffortAssoc.fromDate) : undefined;
        workEffortAssoc.thruDate = workEffortAssoc.thruDate ? moment(workEffortAssoc.thruDate) : undefined;
      });
    }
    return res;
  }
}
