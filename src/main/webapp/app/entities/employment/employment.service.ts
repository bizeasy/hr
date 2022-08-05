import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployment } from 'app/shared/model/employment.model';

type EntityResponseType = HttpResponse<IEmployment>;
type EntityArrayResponseType = HttpResponse<IEmployment[]>;

@Injectable({ providedIn: 'root' })
export class EmploymentService {
  public resourceUrl = SERVER_API_URL + 'api/employments';

  constructor(protected http: HttpClient) {}

  create(employment: IEmployment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employment);
    return this.http
      .post<IEmployment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employment: IEmployment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employment);
    return this.http
      .put<IEmployment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmployment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmployment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(employment: IEmployment): IEmployment {
    const copy: IEmployment = Object.assign({}, employment, {
      fromDate: employment.fromDate && employment.fromDate.isValid() ? employment.fromDate.format(DATE_FORMAT) : undefined,
      thruDate: employment.thruDate && employment.thruDate.isValid() ? employment.thruDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((employment: IEmployment) => {
        employment.fromDate = employment.fromDate ? moment(employment.fromDate) : undefined;
        employment.thruDate = employment.thruDate ? moment(employment.thruDate) : undefined;
      });
    }
    return res;
  }
}
