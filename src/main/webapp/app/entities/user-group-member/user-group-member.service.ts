import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserGroupMember } from 'app/shared/model/user-group-member.model';

type EntityResponseType = HttpResponse<IUserGroupMember>;
type EntityArrayResponseType = HttpResponse<IUserGroupMember[]>;

@Injectable({ providedIn: 'root' })
export class UserGroupMemberService {
  public resourceUrl = SERVER_API_URL + 'api/user-group-members';

  constructor(protected http: HttpClient) {}

  create(userGroupMember: IUserGroupMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userGroupMember);
    return this.http
      .post<IUserGroupMember>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userGroupMember: IUserGroupMember): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userGroupMember);
    return this.http
      .put<IUserGroupMember>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserGroupMember>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserGroupMember[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userGroupMember: IUserGroupMember): IUserGroupMember {
    const copy: IUserGroupMember = Object.assign({}, userGroupMember, {
      fromDate: userGroupMember.fromDate && userGroupMember.fromDate.isValid() ? userGroupMember.fromDate.toJSON() : undefined,
      thruDate: userGroupMember.thruDate && userGroupMember.thruDate.isValid() ? userGroupMember.thruDate.toJSON() : undefined,
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
      res.body.forEach((userGroupMember: IUserGroupMember) => {
        userGroupMember.fromDate = userGroupMember.fromDate ? moment(userGroupMember.fromDate) : undefined;
        userGroupMember.thruDate = userGroupMember.thruDate ? moment(userGroupMember.thruDate) : undefined;
      });
    }
    return res;
  }
}
