import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IShiftHolidays } from 'app/shared/model/shift-holidays.model';

type EntityResponseType = HttpResponse<IShiftHolidays>;
type EntityArrayResponseType = HttpResponse<IShiftHolidays[]>;

@Injectable({ providedIn: 'root' })
export class ShiftHolidaysService {
  public resourceUrl = SERVER_API_URL + 'api/shift-holidays';

  constructor(protected http: HttpClient) {}

  create(shiftHolidays: IShiftHolidays): Observable<EntityResponseType> {
    return this.http.post<IShiftHolidays>(this.resourceUrl, shiftHolidays, { observe: 'response' });
  }

  update(shiftHolidays: IShiftHolidays): Observable<EntityResponseType> {
    return this.http.put<IShiftHolidays>(this.resourceUrl, shiftHolidays, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShiftHolidays>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShiftHolidays[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
