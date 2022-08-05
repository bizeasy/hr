import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacilityGroupMember } from 'app/shared/model/facility-group-member.model';

type EntityResponseType = HttpResponse<IFacilityGroupMember>;
type EntityArrayResponseType = HttpResponse<IFacilityGroupMember[]>;

@Injectable({ providedIn: 'root' })
export class FacilityGroupMemberService {
  public resourceUrl = SERVER_API_URL + 'api/facility-group-members';

  constructor(protected http: HttpClient) {}

  create(facilityGroupMember: IFacilityGroupMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facilityGroupMember);
    return this.http
      .post<IFacilityGroupMember>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(facilityGroupMember: IFacilityGroupMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facilityGroupMember);
    return this.http
      .put<IFacilityGroupMember>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFacilityGroupMember>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFacilityGroupMember[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(facilityGroupMember: IFacilityGroupMember): IFacilityGroupMember {
    const copy: IFacilityGroupMember = Object.assign({}, facilityGroupMember, {
      fromDate: facilityGroupMember.fromDate && facilityGroupMember.fromDate.isValid() ? facilityGroupMember.fromDate.toJSON() : undefined,
      thruDate: facilityGroupMember.thruDate && facilityGroupMember.thruDate.isValid() ? facilityGroupMember.thruDate.toJSON() : undefined,
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
      res.body.forEach((facilityGroupMember: IFacilityGroupMember) => {
        facilityGroupMember.fromDate = facilityGroupMember.fromDate ? moment(facilityGroupMember.fromDate) : undefined;
        facilityGroupMember.thruDate = facilityGroupMember.thruDate ? moment(facilityGroupMember.thruDate) : undefined;
      });
    }
    return res;
  }
}
