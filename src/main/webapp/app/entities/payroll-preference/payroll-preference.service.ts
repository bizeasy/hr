import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPayrollPreference } from 'app/shared/model/payroll-preference.model';

type EntityResponseType = HttpResponse<IPayrollPreference>;
type EntityArrayResponseType = HttpResponse<IPayrollPreference[]>;

@Injectable({ providedIn: 'root' })
export class PayrollPreferenceService {
  public resourceUrl = SERVER_API_URL + 'api/payroll-preferences';

  constructor(protected http: HttpClient) {}

  create(payrollPreference: IPayrollPreference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payrollPreference);
    return this.http
      .post<IPayrollPreference>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(payrollPreference: IPayrollPreference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payrollPreference);
    return this.http
      .put<IPayrollPreference>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPayrollPreference>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPayrollPreference[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(payrollPreference: IPayrollPreference): IPayrollPreference {
    const copy: IPayrollPreference = Object.assign({}, payrollPreference, {
      fromDate: payrollPreference.fromDate && payrollPreference.fromDate.isValid() ? payrollPreference.fromDate.toJSON() : undefined,
      thruDate: payrollPreference.thruDate && payrollPreference.thruDate.isValid() ? payrollPreference.thruDate.toJSON() : undefined,
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
      res.body.forEach((payrollPreference: IPayrollPreference) => {
        payrollPreference.fromDate = payrollPreference.fromDate ? moment(payrollPreference.fromDate) : undefined;
        payrollPreference.thruDate = payrollPreference.thruDate ? moment(payrollPreference.thruDate) : undefined;
      });
    }
    return res;
  }
}
