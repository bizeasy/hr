import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmplLeaveType } from 'app/shared/model/empl-leave-type.model';

type EntityResponseType = HttpResponse<IEmplLeaveType>;
type EntityArrayResponseType = HttpResponse<IEmplLeaveType[]>;

@Injectable({ providedIn: 'root' })
export class EmplLeaveTypeService {
  public resourceUrl = SERVER_API_URL + 'api/empl-leave-types';

  constructor(protected http: HttpClient) {}

  create(emplLeaveType: IEmplLeaveType): Observable<EntityResponseType> {
    return this.http.post<IEmplLeaveType>(this.resourceUrl, emplLeaveType, { observe: 'response' });
  }

  update(emplLeaveType: IEmplLeaveType): Observable<EntityResponseType> {
    return this.http.put<IEmplLeaveType>(this.resourceUrl, emplLeaveType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmplLeaveType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmplLeaveType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
